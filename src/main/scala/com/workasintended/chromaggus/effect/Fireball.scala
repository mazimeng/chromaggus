package com.workasintended.chromaggus.effect

import com.badlogic.ashley.core.Entity

/**
  * Created by mazimeng on 7/26/17.
  */
class Fireball(val caster: Entity,
               val target: Entity,
               val effect: Effect = new InstantDamage()) extends Spell {

}
