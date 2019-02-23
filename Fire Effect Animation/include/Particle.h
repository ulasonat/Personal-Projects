#ifndef PARTICLE_H
#define PARTICLE_H

#include <iostream>
#include <stdlib.h> // To use rand function
#define PI 3.14159265358979323846 // To be able to provide that particles will have direction between two particular degrees which is the part of a circular.
#include <cmath>

class Particle
{
public:

    double x;
    double y;

    double x_speed;
    double y_speed;

    double speed;
    double direction;
    double maximum; // Maximum height it can go

    static double order; // It will determine that where the next particle will be created after the previous one.
    static int sign; // It will determine that whether next particle will be created on the right or left of the previous one.
    int time = 0;
    double start_turning;


    Particle();

public:

    void update(); // This will update the positions of the particles.
};

#endif // PARTICLE_H
