package com.kit.permission

import android.os.Parcel
import android.os.Parcelable
import com.kit.extend.R
import com.kit.utils.ResWrapper

/**
 * @author joeyzhao
 */
class TipInfo : Parcelable {
    var title: String? = null

    var content: String? = null

    /**
     * 取消按钮文本
     */
    var cancel: String? = null

    /**
     * 确定按钮文本
     */
    var ensure: String? = null

    constructor(title: String?, content: String?, cancel: String?, ensure: String?) {
        this.title = title
        this.content = content
        this.cancel = cancel
        this.ensure = ensure
    }

    constructor(content: String?) {
        title = ResWrapper.getString(R.string.tips)
        this.content = content
        cancel = ResWrapper.getString(R.string.cancel)
        ensure = ResWrapper.getString(R.string.ensure)
    }

    constructor() {
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TipInfo> = object : Parcelable.Creator<TipInfo> {
            override fun createFromParcel(source: Parcel): TipInfo = TipInfo(source)
            override fun newArray(size: Int): Array<TipInfo?> = arrayOfNulls(size)
        }
    }
}