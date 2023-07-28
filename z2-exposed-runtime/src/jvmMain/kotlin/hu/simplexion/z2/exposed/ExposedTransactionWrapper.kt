package hu.simplexion.z2.exposed

import hu.simplexion.z2.commons.protobuf.ProtoMessage
import hu.simplexion.z2.commons.protobuf.ProtoMessageBuilder
import hu.simplexion.z2.service.runtime.ServiceContext
import hu.simplexion.z2.service.runtime.ServiceProvider
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction

class ExposedTransactionWrapper(
    val wrappedService: ServiceProvider
) : ServiceProvider {

    override var serviceName: String
        get() = wrappedService.serviceName
        set(value) { wrappedService.serviceName = value }

    override suspend fun dispatch(
        funName: String,
        payload: ProtoMessage,
        context: ServiceContext,
        response: ProtoMessageBuilder
    ) {
        transaction {
            runBlocking {
                wrappedService.dispatch(funName, payload, context, response)
            }
        }
    }
}