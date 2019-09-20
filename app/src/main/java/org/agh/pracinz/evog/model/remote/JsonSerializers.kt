package org.agh.pracinz.evog.model.remote

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class LocalDateSerializer : JsonSerializer<LocalDate>(){
    override fun serialize(value: LocalDate?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        gen!!.writeString(value!!.format(DateTimeFormatter.ISO_DATE))
    }
}

class LocaDateDeserializer : JsonDeserializer<LocalDate>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): LocalDate {
        return LocalDate.parse(p!!.readValueAs(String::class.java))
    }

}