package org.starkeylab.dre.script;
import org.starkeylab.dre.script.CarModel;

def car=CarModel.getExample();
car.price>100000?car.name:null
