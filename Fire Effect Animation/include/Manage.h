#ifndef MANAGE_H
#define MANAGE_H

#include <iostream>
#include "Particle.h"

class Manage
{
    public:

    const static int NPARTICLES = 10;

    Particle *particle = new Particle[NPARTICLES];
};

#endif // MANAGE_H
