package com.jetbrains.dependoapp.presentation.screens.Home

import com.jetbrains.dependoapp.data.repo.DependoRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val dependoRepository: DependoRepository) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState:StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()

 init {
    getDashBoardData()
  }
    private fun getDashBoardData(){
        _homeScreenState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                dependoRepository.getDashboardData().collect{dashResponse->
                    dashResponse.onSuccess {dashResponseData->
                        if (dashResponseData?.statusCode?.equals("2000") == true){
                            _homeScreenState.update {
                                it.copy(delCnt = dashResponseData.dashBoardData?.status?.delCnt)

                            }
                        }
                    }
                    dashResponse.onFailure {onError->
                        _homeScreenState.update {
                            it.copy(isLoading = false, errorMessage = onError.message.toString())
                        }


                    }

                }

            }catch (e:Exception){
                e.stackTraceToString()
            }finally {

            }
        }

    }
}