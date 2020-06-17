package com.example.notesapp
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.multi_state_button.view.*

/**
 * A Button that can transition into different visual states.
 *
 * Supports DISABLED, ACTIVE, BUSY, COMPLETE
 *
 * [disabled](../../../../screenshots/MultiStateButton-disabled.png "disabled")
 * [active](../../../../screenshots/MultiStateButton-active.png "active")
 * [busy](../../../../screenshots/MultiStateButton-busy.png "busy")
 * [complete](../../../../screenshots/MultiStateButton-complete.png "complete")
 */
class MultiStateButton : FrameLayout {


    // (required for XML)
    constructor(context: Context) : super(context) {
        View.inflate(context, R.layout.multi_state_button, this)
    }
   /* private var srcProgressDisabled: Drawable = ColorDrawable(ContextCompat.getColor(context, R.color.darkRed))
    private var srcProgressActive: Drawable = ContextCompat.getDrawable(context, R.drawable.primary_button_states)!!
    private var srcProgressBusy: Drawable = ColorDrawable(ContextCompat.getColor(context, R.color.hsbc_design_primary_red_busy))
    private var srcProgressComplete: Drawable = ColorDrawable(ContextCompat.getColor(context, R.color.hsbc_design_jade))
    private var onCompleteListener: (() -> Unit)? = null

    var state: State = State.DISABLED
        set(value) {
            field = value
            setStateBackground()
        }
    var disabledText: String? = ""
        set(value) {
            field = value
            setStateBackground()
        }
    var activeText: String? = ""
        set(value) {
            field = value
            setStateBackground()
        }
    var busyText: String? = ""
        set(value) {
            field = value
            setStateBackground()
        }
    var completeText: String? = ""
        set(value) {
            field = value
            setStateBackground()
        }

    // (required for XML)
    constructor(context: Context) : super(context) {
        View.inflate(context, R.layout.multi_state_button, this)
    }

    // (required for XML)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    // Providing a style (required for XML)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        View.inflate(context, R.layout.multi_state_button, this)
        val a = context.theme.obtainStyledAttributes(
            attrs!!,
            R.styleable.MultiStateButton,
            defStyleAttr, 0)
        try {
            val ordinal = a.getInt(R.styleable.MultiStateButton_progress_state, 0)
            if (ordinal >= 0 && ordinal < State.values().size) {
                state = State.values()[ordinal]
            }
            disabledText = a.getString(R.styleable.MultiStateButton_disabled_text)
            activeText = a.getString(R.styleable.MultiStateButton_active_text)
            busyText = a.getString(R.styleable.MultiStateButton_busy_text)
            completeText = a.getString(R.styleable.MultiStateButton_complete_text)
        } finally {
            a.recycle()
        }
        setStateBackground()
    }

    private fun setStateBackground() {
        bt_multi_state.apply {
            when (state) {
                State.DISABLED -> {
                    isEnabled = false
                    background = srcProgressDisabled
                    text = disabledText
                    contentDescription = disabledText
                    hideTick()
                    hideProgress()
                }
                State.ACTIVE -> {
                    isEnabled = true
                    background = srcProgressActive
                    text = activeText
                    contentDescription = activeText
                    hideTick()
                    hideProgress()
                }
                State.BUSY -> {
                    isEnabled = false
                    background = srcProgressBusy
                    text = busyText
                    contentDescription = busyText
                    hideTick()
                    showProgress()
                }
                State.COMPLETE -> {
                    isEnabled = false
                    background = srcProgressComplete
                    text = completeText
                    hideProgress()
                    showTick()
                }
            }
        }
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun showTick() {
        checkMark.apply {
            visible()
            setChecked(true, true)
        }
    }

    private fun hideTick() {
        checkMark.gone()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        bt_multi_state.setOnClickListener(l)
    }

    @Deprecated("Use the functional style setOnCompleteListener(l: (() -> Unit)?)", ReplaceWith("setOnCompleteListener { TODO(\"add oncompletion code here\") }"))
    fun setOnCompleteListener(l:MultiStateButton.OnCompleteListener) {
        onCompleteListener = { l.onCompleted() }
    }

    fun setOnCompleteListener(l: (() -> Unit)?) {
        onCompleteListener = l
    }

    fun setOnClickListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface OnCompleteListener {
        fun onCompleted()
    }

    enum class State {
        DISABLED, ACTIVE, BUSY, COMPLETE
    }*/
}