package mcssoft.com.racereminderac.interfaces

import android.view.View

interface ISelect {

    interface ItemSelect {
        /**
         * Single select (touch) operation.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemSelect(lPos: Int)

        /**
         * Single select operation.
         * @param view The view that was clicked on.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemSelect(view: View, lPos: Int) {}
    }

    interface ItemLongSelect {
        /**
         * Long press operation.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemLongSelect(lPos: Int)

        /**
         * Long press operation.
         * @param view The view that was pressed on.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemLongSelect(view: View, lPos: Int) {}
    }

}