package hu.simplexion.z2.exposed

import hu.simplexion.z2.schematic.runtime.Schematic

class TestSchematic : Schematic<TestSchematic>() {

    var id by uuid<TestSchematic>()

    var booleanField by boolean()
    var intField by int()
    var stringField by string()
    var uuidField by uuid<TestSchematic>()

}