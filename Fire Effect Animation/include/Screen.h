#ifndef SCREEN_H
#define SCREEN_H

#include <iostream>
#include <SDL2/SDL.h>
#include <cstring> // To use 'memset' function.
#include <cmath>

class Screen
{

public:
    const static int WIDTH = 500; // Screen width
    const static int HEIGHT = 500; // Screen height
    Uint32 *buffer;
private:
    SDL_Window *window;
    SDL_Renderer *renderer;
    SDL_Texture *texture;


public:
    void init(); // Makes the initialization
    bool if_exit(); // Checks if the user press the "X" button.
    void update();
    void setColor(int time, Uint8 &red, Uint8 &green, Uint8 &blue);
    void setPixel(int x, int y, Uint8 red, Uint8 green, Uint8 blue);
    void close();
    void boxBlur();


};

#endif // SCREEN_H
