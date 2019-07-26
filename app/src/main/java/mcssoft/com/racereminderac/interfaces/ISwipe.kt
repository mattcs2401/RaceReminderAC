package mcssoft.com.racereminderac.interfaces

interface ISwipe {

    /**
     * For when a view (a recycler view list item) is swiped.
     * @param pos The position of the item in the list.
     */
    fun onViewSwiped(pos: Int) {}
}