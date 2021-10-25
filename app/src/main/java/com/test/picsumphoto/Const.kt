/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.picsumphoto

/**
 * manage all constant
 *
 */
interface Const {

    interface ItemViewType {

        companion object {

            const val UNKNOWN = -1              //未知类型，使用EmptyViewHolder容错处理。

            const val CUSTOM_HEADER = 0         //自定义头部类型。

            const val TEXT_CARD_HEADER1 = 1

            const val TEXT_CARD_HEADER2 = 2

            const val TEXT_CARD_HEADER3 = 3

            const val TEXT_CARD_HEADER4 = 4     //type:textCard -> dataType:TextCard,type:header4

            const val TEXT_CARD_HEADER5 = 5     //type:textCard -> dataType:TextCard -> type:header5

            const val TEXT_CARD_HEADER6 = 6

            const val TEXT_CARD_HEADER7 =
                7    //type:textCard -> dataType:TextCardWithRightAndLeftTitle,type:header7

            const val TEXT_CARD_HEADER8 =
                8    //type:textCard -> dataType:TextCardWithRightAndLeftTitle,type:header8

            const val TEXT_CARD_FOOTER1 = 9

            const val TEXT_CARD_FOOTER2 = 10    //type:textCard -> dataType:TextCard,type:footer2

            const val TEXT_CARD_FOOTER3 =
                11    //type:textCard -> dataType:TextCardWithTagId,type:footer3

            const val BANNER = 12               //type:banner -> dataType:Banner

            const val BANNER3 = 13              //type:banner3-> dataType:Banner

            const val FOLLOW_CARD =
                14          //type:followCard -> dataType:FollowCard -> type:video -> dataType:VideoBeanForClient

            const val TAG_BRIEFCARD = 15        //type:briefCard -> dataType:TagBriefCard

            const val TOPIC_BRIEFCARD = 16      //type:briefCard -> dataType:TopicBriefCard

            const val COLUMN_CARD_LIST = 17      //type:columnCardList -> dataType:ItemCollection

            const val VIDEO_SMALL_CARD = 18     //type:videoSmallCard -> dataType:VideoBeanForClient

            const val INFORMATION_CARD = 19     //type:informationCard -> dataType:InformationCard

            const val AUTO_PLAY_VIDEO_AD =
                20   //type:autoPlayVideoAd -> dataType:AutoPlayVideoAdDetail

            const val HORIZONTAL_SCROLL_CARD =
                21    //type:horizontalScrollCard -> dataType:HorizontalScrollCard

            const val SPECIAL_SQUARE_CARD_COLLECTION =
                22   //type:specialSquareCardCollection -> dataType:ItemCollection

            const val UGC_SELECTED_CARD_COLLECTION =
                23   //type:ugcSelectedCardCollection -> dataType:ItemCollection

            const val MAX = 100   //避免外部其他类型与此处包含的某个类型重复。\

            const val TYPE_1 = 0
            const val TYPE_2 = 1
        }
    }

}