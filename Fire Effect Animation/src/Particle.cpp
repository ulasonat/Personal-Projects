#include "Particle.h"
#include <iostream>

double Particle::order = 0;
int Particle::sign = 1;

Particle::Particle() {

    x = order;
    y = 0.6;

    if (sign % 2 == 1) {
    order += (double)sign/1000; sign++; }
    else {
    order -= (double)sign/1000; sign++; }

    direction = fmod((2*PI*rand()/RAND_MAX), 2*PI/3) + PI/6; // By operation order, we expect that the result will be "double" type.
    speed = 0.01*rand()/RAND_MAX;
    start_turning = rand()%50 + 30;


}

void Particle::update() {

    double x_speed = speed * cos(direction);
    double y_speed = -speed * sin(direction);

    x += x_speed;
    y += y_speed;

    if (time > start_turning) {direction += (double)rand()/RAND_MAX;}

    time++;

}
