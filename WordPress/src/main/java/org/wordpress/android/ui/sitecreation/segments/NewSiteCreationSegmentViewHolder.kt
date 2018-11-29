package org.wordpress.android.ui.sitecreation.segments

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.wordpress.android.R
import org.wordpress.android.ui.sitecreation.segments.NewSiteCreationSegmentsViewModel.ItemUiState
import org.wordpress.android.ui.sitecreation.segments.NewSiteCreationSegmentsViewModel.ItemUiState.HeaderUiState
import org.wordpress.android.ui.sitecreation.segments.NewSiteCreationSegmentsViewModel.ItemUiState.SegmentUiState
import org.wordpress.android.util.AppLog
import org.wordpress.android.util.image.ImageManager
import org.wordpress.android.util.image.ImageType.IMAGE

sealed class NewSiteCreationSegmentViewHolder(internal val parent: ViewGroup, @LayoutRes layout: Int) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false)) {
    abstract fun onBind(uiState: ItemUiState)

    class SegmentsItemViewHolder(
        parentView: ViewGroup,
        private val imageManager: ImageManager
    ) : NewSiteCreationSegmentViewHolder(parentView, R.layout.new_site_creation_segment_item) {
        private val container = itemView.findViewById<ViewGroup>(R.id.container)
        private val icon = itemView.findViewById<ImageView>(R.id.icon)
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val subtitle = itemView.findViewById<TextView>(R.id.subtitle)
        private val divider = itemView.findViewById<View>(R.id.divider)

        override fun onBind(uiState: ItemUiState) {
            uiState as SegmentUiState
            title.text = uiState.title
            subtitle.text = uiState.subtitle
            imageManager.load(icon, IMAGE, uiState.iconUrl)
            container.setOnClickListener {
                uiState.onItemTapped?.invoke() ?: AppLog.e(AppLog.T.MAIN, "onClickListener method reference is null.")
            }
            divider.visibility = if (uiState.showDivider) View.VISIBLE else View.GONE
        }
    }

    class SegmentsHeaderViewHolder(
        parentView: ViewGroup
    ) : NewSiteCreationSegmentViewHolder(parentView, R.layout.new_site_creation_segments_header_item) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val subtitle = itemView.findViewById<TextView>(R.id.subtitle)

        override fun onBind(uiState: ItemUiState) {
            uiState as HeaderUiState
            title.text = parent.context.getText(uiState.titleResId)
            subtitle.text = parent.context.getText(uiState.subtitleResId)
        }
    }

    class SegmentsProgressViewHolder(
        parentView: ViewGroup
    ) : NewSiteCreationSegmentViewHolder(parentView, R.layout.new_site_creation_segments_progress) {
        override fun onBind(uiState: ItemUiState) {}
    }
}
