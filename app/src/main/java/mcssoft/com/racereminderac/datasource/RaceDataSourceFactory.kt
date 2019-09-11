package mcssoft.com.racereminderac.datasource

import androidx.paging.DataSource
import mcssoft.com.racereminderac.entity.RaceDetails

class RaceDataSourceFactory : DataSource.Factory<Long, RaceDetails>() {

    override fun create(): DataSource<Long, RaceDetails> {
        return RaceDataSource()
    }
}