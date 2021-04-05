package com.unidavi.tc.conto.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.unidavi.tc.conto.R
import com.unidavi.tc.conto.database.Discount
import com.unidavi.tc.conto.database.Product

@BindingAdapter("discountEstablishmentName")
fun TextView.setDiscountEstablishmentName(item: Discount?) {
    item?.let {
        text = item.establishment.name
    }
}

@BindingAdapter("discountActiveUntil")
fun TextView.setDiscountActiveUntil(item: Discount?) {
    item?.let {
        text = if (item.active) "Validade indeterminada" else "Desconto inativo"
    }
}

@BindingAdapter("discountDescription")
fun TextView.setDiscountDescription(item: Discount?) {
    item?.let {
        text = """Desconto de ${item.percentage}%"""
    }
}


@BindingAdapter("discountDetailDescription")
fun TextView.setDiscountDetailDescription(item: Discount?) {
    item?.let {
        text =
            item.description + "\n" + """Desconto de ${item.percentage}% em qualquer item oferecido"""
    }
}

@BindingAdapter("discountDetailCode")
fun TextView.setDiscountDetailCode(item: Discount?) {
    item?.let {
        text = item.code
    }
}

@BindingAdapter("discountDetailActive")
fun TextView.setDiscountDetailActive(item: Discount?) {
    item?.let {
        text = if (item.active) "Ativo" else "Inativo"
    }
}

@BindingAdapter("discountDetailActiveImage")
fun ImageView.setDiscountDetailActiveImage(item: Discount?) {
    item?.let {
        setImageResource(
            when (item.active) {
                true -> R.drawable.ic_active
                else -> R.drawable.ic_inactive
            }
        )
    }
}

@BindingAdapter("menuName")
fun TextView.setProductName(item: Product?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("menuPrice")
fun TextView.setProductPrice(item: Product?) {
    val roundedValue = String.format("%.2f", item?.price)
    item?.let {
        text = """R$ $roundedValue"""
    }
}

@BindingAdapter("menuCategory")
fun TextView.setCategory(item: Product?) {
    item?.let {
        text = item.categoryString
    }
}


