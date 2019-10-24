package org.agh.pracinz.evog.viewmodel.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import org.agh.pracinz.evog.model.data.Category
import org.agh.pracinz.evog.model.data.EventFilter
import org.agh.pracinz.evog.model.data.Localization
import org.agh.pracinz.evog.model.repository.EventRepository
import java.time.LocalDateTime


class EventListViewModel(private val repository: EventRepository) : ViewModel() {


    val state = EventListState()

    fun getEventsFiltered() = repository.getFiltered(state.toFilter())

    @RequiresApi(Build.VERSION_CODES.P)
    fun getIcon(name: String) = repository.getIcon(name)
}


private fun EventListState.toFilter() = EventFilter(
    name,
    minAllowedAge,
    maxAllowedAge,
    startTime,
    endTime,
    localizationRadius,
    maxNumberOfPeople,
    minNumberOfPeople,
    category,
    localization,
    isMy
)

data class EventListState(
    var localization: Localization = Localization(0.0, 0.0),
    var name: String? = null,
    var minAllowedAge: Int? = null,
    var maxAllowedAge: Int? = null,
    var startTime: LocalDateTime? = LocalDateTime.now(),
    var endTime: LocalDateTime? = LocalDateTime.now().plusDays(7),
    var localizationRadius: Int = 10,
    var maxNumberOfPeople: Int? = null,
    var minNumberOfPeople: Int? = null,
    var category: Category? = null,
    var isMy : Boolean = false
)




