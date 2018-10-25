package mcssoft.com.racereminderac.interfaces

import android.view.View

interface IClick {

    interface ItemSelect {
        /**
         * Single select (touch) operation.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemSelect(lPos: Int) {} // braces make this optional

        /**
         * Single select operation.
         * @param view The view that was clicked on.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemSelect(view: View, lPos: Int)
    }

    interface ItemLongClick {
        /**
         * Long press operation.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemLongClick(lPos: Int)

        /**
         * Long press operation.
         * @param view The view that was pressed on.
         * @param lPos The view's position in a list (if applicable).
         */
        fun onItemLongClick(view: View, lPos: Int) {} // braces make this optional
    }

}