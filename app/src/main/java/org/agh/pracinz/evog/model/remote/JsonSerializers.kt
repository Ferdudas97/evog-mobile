package org.agh.pracinz.evog.model.remote

//
//import com.fasterxml.jackson.core.JsonGenerator
//import com.fasterxml.jackson.core.JsonParser
//import com.fasterxml.jackson.databind.DeserializationContext
//import com.fasterxml.jackson.databind.JsonDeserializer
//import com.fasterxml.jackson.databind.JsonSerializer
//import com.fasterxml.jackson.databind.SerializerProvider
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


//class LocalDateSerializer : JsonSerializer<LocalDate>(){
//    override fun serialize(value: LocalDate?, gen: JsonGenerator?, serializers: SerializerProvider?) {
//        gen!!.writeString(value!!.format(DateTimeFormatter.ISO_DATE))
//    }
//}
//
//class LocaDateDeserializer : JsonDeserializer<LocalDate>() {
//    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): LocalDate {
//        return LocalDate.parse(p!!.readValueAs(String::class.java))
//    }

//}

class LocalDateAdapter : TypeAdapter<LocalDate>() {
    @Throws(IOException::class)
    override fun write(jsonWriter: JsonWriter, localDate: LocalDate?) {
        if (localDate == null) {
            jsonWriter.nullValue()
        } else {
            jsonWriter.value(localDate.toString())
        }
    }

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): LocalDate? {
        if (jsonReader.peek() === JsonToken.NULL) {
            jsonReader.nextNull()
            return null
        } else {
            val formater = DateTimeFormatter.ofPattern("yyyy-MM-dd" )
            return LocalDate.parse(jsonReader.nextString(),formater)
        }
    }
}

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {
    @Throws(IOException::class)
    override fun write(jsonWriter: JsonWriter, localDate: LocalDateTime?) {
        if (localDate == null) {
            jsonWriter.nullValue()
        } else {
            jsonWriter.value(localDate.toString())
        }
    }

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): LocalDateTime? {
        if (jsonReader.peek() === JsonToken.NULL) {
            jsonReader.nextNull()
            return null
        } else {
            val formater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
            return LocalDateTime.parse(jsonReader.nextString(),formater)
        }
    }
}