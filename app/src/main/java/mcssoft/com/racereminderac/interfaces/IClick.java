package mcssoft.com.racereminderac.interfaces;

import android.view.View;

public interface IClick {

    public interface ItemClick {
        /**
         * Single click operation.
         * @param view The view that was clicked on.
         * @param lPos The view's position in a list (if applicable).
         */
        void onItemClick(View view, int lPos);
    }

    public interface ItemLongClick {
        /**
         * Long press operation.
         * @param view The view that was pressed on.
         * @param lPos The view's position in a list (if applicable).
         */
        void onItemClick(View view, int lPos);
    }

}
