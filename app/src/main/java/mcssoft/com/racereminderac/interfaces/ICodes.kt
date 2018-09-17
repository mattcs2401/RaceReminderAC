package mcssoft.com.racereminderac.interfaces

interface ICodes {

    companion object { }

    interface ICityCodes {
        fun onFinishCityCodes(code: String)
    }

    interface IRaceCodes {
        fun onFinishRaceCodes(code: String)
    }

}