#include <iostream>
#include <SDL2/SDL.h>
#include "Screen.h"
#include "Particle.h"
#include <time.h>

using namespace std;

int main (int argc, char* argv[])
{
    srand(time(NULL)); // Changes the algorithm of the program
    Screen screen;
    screen.init();

    int NPARTICLES = 500;
    Particle *particle = new Particle[NPARTICLES];

    bool program_running = true;
    unsigned char red, green, blue;

    while (program_running) {

    int time = SDL_GetTicks(); // Gets the number of milliseconds since the program started to run

    for (int i=0; i<NPARTICLES; i++) {particle[i].update();} // Updates the positions of all pixels

    screen.setColor(time, red, green, blue); // Sets the new color of pixels

    for (int i=0; i<NPARTICLES; i++) {
        int x = (particle[i].x + 1) * (Screen::WIDTH/2);
        int y = (particle[i].y) * (Screen::WIDTH/2) + (Screen::HEIGHT/2);
        screen.setPixel(x, y, red, green, blue);
    }

    screen.boxBlur();

    screen.update();

    if (screen.if_exit())
        program_running = false;

    }

    screen.close();

    return 0;
}
