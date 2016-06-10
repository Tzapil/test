(ns telegram-bot-lib.inline
    (:require [telegram-bot-lib.helpers :as helpers]))

(defn create_result_article
    ([title message_text]
        (create_result_article title message_text nil nil))
    ([title message_text description]
        (create_result_article title message_text description nil))
    ([title message_text description parse_mode]
        {
            :type "article"
            :id (helpers/uuid)
            :title title
            :description description
            :input_message_content {
                :message_text message_text
                :parse_mode parse_mode       
            }
        }))

(defn create_result_photo
    ([photo_url thumb_url]
        (create_result_photo photo_url thumb_url nil nil))
    ([photo_url thumb_url title]
        (create_result_photo photo_url thumb_url title nil))
    ([photo_url thumb_url title description]
        {
            :type "photo"
            :id (helpers/uuid)
            :title title
            :photo_url photo_url
            :thumb_url thumb_url
            :description description
        }))

(defn create_result_gif
    ([gif_url thumb_url]
        (create_result_gif gif_url thumb_url nil nil))
    ([gif_url thumb_url title]
        (create_result_gif gif_url thumb_url title nil))
    ([gif_url thumb_url title caption]
        {
            :type "gif"
            :id (helpers/uuid)
            :title title
            :gif_url gif_url
            :thumb_url thumb_url
            :caption caption
        }))

(defn create_result_mpeg4_gif
    ([mpeg4_url thumb_url]
        (create_result_mpeg4_gif mpeg4_url thumb_url title nil nil))
    ([mpeg4_url thumb_url title]
        (create_result_mpeg4_gif mpeg4_url thumb_url title nil))
    ([mpeg4_url thumb_url title caption]
        {
            :type "mpeg4_gif"
            :id (helpers/uuid)
            :title title
            :mpeg4_url mpeg4_url
            :thumb_url thumb_url
            :caption caption
        }))

(defn create_result_video
    ([video_url mime_type thumb_url title]
        (create_result_video video_url mime_type thumb_url title nil nil))
    ([video_url mime_type thumb_url title caption]
        (create_result_video video_url mime_type thumb_url title caption nil))
    ([video_url mime_type thumb_url title caption description]
        {
            :type "video"
            :id (helpers/uuid)
            :title title
            :video_url video_url
            :mime_type mime_type
            :thumb_url thumb_url
            :caption caption
            :description description
        }))

(defn create_result_audio
    ([audio_url title]
        (create_result_audio audio_url title nil))
    ([audio_url title performer]
        {
            :type "audio"
            :id (helpers/uuid)
            :title title
            :audio_url audio_url
            :performer performer
        }))

(defn create_result_voice
    [voice_url title]
        {
            :type "voice"
            :id (helpers/uuid)
            :title title
            :voice_url voice_url
        })

(defn create_result_document
    ([document_url title mime_type]
        (create_result_document document_url title mime_type nil nil))
    ([document_url title mime_type caption]
        (create_result_document title document_url title mime_type caption nil))
    ([document_url title mime_type caption description]
        {
            :type "document"
            :id (helpers/uuid)
            :title title
            :document_url document_url
            :mime_type mime_type
            :caption caption
            :description description
        }))

(defn create_result_venue
    ([latitude longitude title address]
        (create_result_venue latitude longitude title address nil))
    ([latitude longitude title address foursquare_id]
        {
            :type "location"
            :id (helpers/uuid)
            :title title
            :latitude latitude
            :longitude longitude
            :address address
            :foursquare_id foursquare_id
        }))

(defn create_result_location
    [latitude longitude title]
        {
            :type "location"
            :id (helpers/uuid)
            :title title
            :latitude latitude
            :longitude longitude
        })

(defn create_result_contact
    ([phone_number first_name]
        (create_result_contact phone_number first_name nil))
    ([phone_number first_name last_name]
        {
            :type "contact"
            :id (helpers/uuid)
            :phone_number phone_number
            :first_name first_name
            :last_name last_name
        }))

(defn create_result_cached_photo
    ([audio_file_id]
        (create_result_cached_audio audio_file_id nil nil))
    ([audio_file_id]
        (create_result_cached_audio audio_file_id message_text nil))
    ([audio_file_id title description caption]
        {
            :type "audio"
            :id (helpers/uuid)
            :audio_file_id audio_file_id
            :title title
            :description description
        }))

(defn create_result_cached_audio
    ([audio_file_id]
        (create_result_cached_audio audio_file_id nil nil))
    ([audio_file_id message_text]
        (create_result_cached_audio audio_file_id message_text nil))
    ([audio_file_id message_text parse_mode]
        {
            :type "audio"
            :id (helpers/uuid)
            :audio_file_id audio_file_id
            :input_message_content {
                :message_text message_text
                :parse_mode parse_mode       
            }
        }))