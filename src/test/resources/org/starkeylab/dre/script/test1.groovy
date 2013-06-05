package org.starkeylab.dre.script;
def records = new XmlParser().parseText('<records><car><name>HSV Maloo</name></car></records>');
def carName=records.car.name.text();
println carName;
return carName