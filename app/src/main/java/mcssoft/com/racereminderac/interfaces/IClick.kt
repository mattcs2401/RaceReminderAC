package mcssoft.com.racereminderac.interfaces

import android.view.View

interface IClick {

    interface ItemClick {
        /**
         * Single click operation.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemClick(lPos: Int)

        /**
         * Single click operation.
         * @param view The view that was clicked on.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemClick(view: View, lPos: Int) {} // braces make this optional
    }

    interface ItemLongClick {
        /**
         * Long press operation.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemClick(lPos: Int)

        /**
         * Long press operation.
         * @param view The view that was pressed on.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemClick(view: View, lPos: Int) {}
    }

}