package hu.simplexion.z2.exposed

import hu.simplexion.z2.commons.util.UUID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Test
import kotlin.test.assertEquals

class SchematicUuidTableTest {

    @Test
    fun testMapping() {
        h2Test(TestTable)

        val schematic = TestSchematic().apply {
            booleanField = true
            intField = 12
            stringField = "abc"
            uuidField = UUID()
        }

        transaction {
            schematic.id = TestTable.insert(schematic)

            val list = TestTable.list()
            assertEquals(1, list.size)
            assertEquals(schematic.id, list.first().id)

            schematic.intField = 456
            TestTable.update(schematic.id, schematic)

            val readback = TestTable.get(schematic.id)
            assertEquals(456, readback.intField)

            TestTable.remove(schematic.id)
            assertEquals(0, TestTable.list().size)
        }
    }
}