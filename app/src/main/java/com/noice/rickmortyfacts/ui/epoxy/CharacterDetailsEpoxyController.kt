package com.noice.rickmortyfacts.ui.epoxy

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.carousel
import com.bumptech.glide.Glide
import com.noice.rickmortyfacts.R
import com.noice.rickmortyfacts.databinding.ModelCharacterDetailsDatapointBinding
import com.noice.rickmortyfacts.databinding.ModelCharacterDetailsHeaderBinding
import com.noice.rickmortyfacts.databinding.ModelCharacterDetailsImageBinding
import com.noice.rickmortyfacts.model.CharacterModel

class CharacterDetailsEpoxyController: EpoxyController() {

    var isLoading:Boolean? = true
    set(value) {
        field = value
        if (field != null) {
            requestModelBuild()
        }
    }

    var characterData = CharacterModel()
    set(value) {
        field = value
        isLoading = false
        requestModelBuild()

    }
    override fun buildModels() {

        HeaderEpoxyModel(
            characterData.name,
            characterData.gender,
            characterData.status
        ).id("header").addTo(this)

        val imageModelList = mutableListOf<EpoxyModel<View>>()
        for(i in 0..10){
            imageModelList.add(i,ImageEpoxyModel(
                this@CharacterDetailsEpoxyController.characterData.image
            ).id("image$i"))
        }

        carousel {
            id("carousel")
            numViewsToShowOnScreen(1.2F)
            models(imageModelList)
        }

        val dataPointModelList = mutableListOf<EpoxyModel<View>>()
        for (i in 0..10){
            if (i%2 == 0){
                dataPointModelList.add(i,DataPointEpoxyModel(
                    "Species",
                    characterData.species
                ).id("dp$i"))
            }else{
                dataPointModelList.add(i,DataPointEpoxyModel(
                    "Origin",
                    characterData.origin.name
                ).id("dp$i"))
            }

        }

        carousel {
            id("dataPoints")
            numViewsToShowOnScreen(1.2F)
            models(dataPointModelList.subList(0,4))
        }

        carousel {
            id("dataPoints2")
            numViewsToShowOnScreen(1.2F)
            models(dataPointModelList.subList(5,10))

        }

        for (i in 0..10) {
            if (i%2 == 0){
                DataPointEpoxyModel(
                    "Species",
                    characterData.species
                ).id("dp$i").addTo(this)
            }else{
                DataPointEpoxyModel(
                    "Origin",
                    characterData.origin.name
                ).id("dp$i").addTo(this)
            }
        }



    }


    data class HeaderEpoxyModel(
        val name:String,
        val gender:String,
        val status:String
    ): ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header){
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTv.text = name
            statusTv.text = status

            when {
                gender.equals("male",true) -> {
                    genderIv.setImageResource(R.drawable.ic_baseline_male_24)
                }
                gender.equals("female",true) -> {
                    genderIv.setImageResource(R.drawable.ic_baseline_female_24)
                }
                else -> {
                    genderIv.setImageResource(R.drawable.ic_baseline_transgender_24)
                }
            }
        }
    }

    data class ImageEpoxyModel(
        val image:String,
    ):ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image){
        override fun ModelCharacterDetailsImageBinding.bind() {
            Glide.with(root)
                .load(image)
                .into(imageIv)
        }
    }

    data class DataPointEpoxyModel(
        val title:String,
        val value:String
    ):ViewBindingKotlinModel<ModelCharacterDetailsDatapointBinding>(R.layout.model_character_details_datapoint){
        override fun ModelCharacterDetailsDatapointBinding.bind() {
            labelTv.text = title
            valueTv.text = value
        }
    }
}