package org.kl.smartbuy.event.purchase

import java.util.*
import android.app.DatePickerDialog
import android.view.View
import android.widget.DatePicker

import org.kl.smartbuy.view.EditPurchaseActivity
import java.text.SimpleDateFormat

class SelectDatePurchaseListener(
    private val activity: EditPurchaseActivity
) : View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private val calendar = GregorianCalendar.getInstance()

    override fun onClick(view: View?) {
        DatePickerDialog(activity, this,
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ).show()
    }

    override fun onDateSet(picker: DatePicker?, year: Int, month: Int, day: Int) {
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day

        val format = "dd.MM.yyyy"
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())

        activity.dateTextView.setText(dateFormat.format(calendar.time))
    }
}