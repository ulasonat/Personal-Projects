#include "Screen.h"

void Screen::init()
{

    if (SDL_INIT_EVERYTHING < 0)
    {
        std::cout << "Initialization error occurred. SDL could not been uploaded.";
        return;
    }

    window = SDL_CreateWindow("Particle Fire Effect", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, WIDTH, HEIGHT, SDL_WINDOW_SHOWN);

    if (window == NULL)
    {
        std::cout << "Window could not been opened properly.";
        SDL_Quit();
        return;
    }

    renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_PRESENTVSYNC);

    if (renderer == NULL)
    {
        std::cout << "Renderer initialization error!";
        SDL_DestroyWindow(window);
        SDL_Quit();
        return;
    }

    texture = SDL_CreateTexture(renderer, SDL_PIXELFORMAT_RGB888, SDL_TEXTUREACCESS_STATIC, WIDTH, HEIGHT);

    if (texture == NULL)
    {
        std::cout << "Texture initialization error!";
        SDL_DestroyRenderer(renderer);
        SDL_DestroyWindow(window);
        SDL_Quit();
    }

    buffer = new Uint32[WIDTH*HEIGHT];

    memset(buffer, 0, WIDTH*HEIGHT*sizeof(Uint32)); // Sets all elements of the buffer array to 0 which outputs as black in color.

}

bool Screen::if_exit()
{

    SDL_Event event;

    while (SDL_PollEvent(&event))
    {
        if (event.type == SDL_QUIT)
            return true;

        else
            return false;
    }
}

void Screen::setColor(int time, Uint8 &red, Uint8 &green, Uint8 &blue)
{
    red = 255;
    green = 1 + sin(time * 0.0004) * 127.5;
    blue = 0;
}

void Screen::setPixel(int x, int y, Uint8 red, Uint8 green, Uint8 blue)
{
    if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
        return; // Ensures that over screen pixel won't be drawn.

    Uint32 color = 0;

    color += red;
    color <<= 8;
    color += green;
    color <<= 8;
    color += blue;
    // Since SDL does not "support", we don't need it.
    // std::cout << std::hex << color << std::endl; Can be used to see the color values of each pixel
    buffer[y*WIDTH + x] = color;

}

void Screen::boxBlur()
{
    for (int y=0; y<WIDTH; y++) {
        for (int x=0; x<HEIGHT; x++) {
            int redTotal = 0;
            int greenTotal = 0;
            int blueTotal = 0;

            for (int row=-1; row<=1; row++) {
                for (int col=-1; col<=1; col++) {
                    int currentX = x + col;
                    int currentY = y + row;

                    if (currentX >= 0 && currentX < Screen::WIDTH && currentY >0 && currentY < Screen::HEIGHT) {
                        Uint32 color = buffer[currentY * WIDTH + currentX];

                        Uint8 red = color >> 16;
                        Uint8 green = color >> 8;
                        Uint8 blue = color; // Since Uint8 will only consider the last two digits because of its memory capasite 1 byte.

                        redTotal += red;
                        greenTotal += green;
                        blueTotal += blue;

                    }


                }
            }

            Uint8 red = redTotal / 9;
            Uint8 green = greenTotal / 9;
            Uint8 blue = blueTotal / 9;

            setPixel(x,y,red,green,blue);
        }
    }
}

void Screen::update()
{

    SDL_UpdateTexture(texture, NULL, buffer, WIDTH*sizeof(Uint32)); // Updates the information of texture which is the color information.
    SDL_RenderClear(renderer); // Clears the renderer first.
    SDL_RenderCopy(renderer, texture, NULL, NULL); // Copies the content of texture into renderer.
    SDL_RenderPresent(renderer);

}

void Screen::close()
{
    delete [] buffer;
    SDL_DestroyRenderer(renderer);
    SDL_DestroyTexture(texture);
    SDL_DestroyWindow(window);
    SDL_Quit();
}
