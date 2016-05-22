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

(defn create_result_audio
    ([title audio_url]
        (create_result_audio title audio_url nil nil))
    ([title audio_url message_text]
        (create_result_audio title audio_url message_text nil))
    ([title audio_url message_text parse_mode]
        {
            :type "audio"
            :id (helpers/uuid)
            :title title
            :audio_url audio_url
            :input_message_content {
                :message_text message_text
                :parse_mode parse_mode       
            }
        }))

(defn create_result_contact
    ([phone_number first_name]
        (create_result_contact phone_number first_name nil nil))
    ([phone_number first_name message_text]
        (create_result_contact phone_number first_name message_text nil))
    ([phone_number first_name message_text parse_mode]
        {
            :type "contact"
            :id (helpers/uuid)
            :phone_number phone_number
            :first_name first_name
            :input_message_content {
                :message_text message_text
                :parse_mode parse_mode       
            }
        }))

(defn create_result_gif
    ([title gif_url]
        (create_result_gif title gif_url nil nil))
    ([title gif_url message_text]
        (create_result_gif title gif_url message_text nil))
    ([title gif_url message_text parse_mode]
        {
            :type "gif"
            :id (helpers/uuid)
            :title title
            :gif_url gif_url
            :input_message_content {
                :message_text message_text
                :parse_mode parse_mode       
            }
        }))

(defn create_result_document
    ([title document_url mime_type]
        (create_result_document title document_url mime_type nil nil))
    ([title document_url mime_type message_text]
        (create_result_document title document_url mime_type message_text nil))
    ([title document_url mime_type message_text parse_mode]
        {
            :type "document"
            :id (helpers/uuid)
            :title title
            :document_url document_url
            :mime_type mime_type
            :input_message_content {
                :message_text message_text
                :parse_mode parse_mode       
            }
        }))

(defn create_result_location
    ([title latitude longitude]
        (create_result_location title latitude longitude nil nil))
    ([title latitude longitude message_text]
        (create_result_location title latitude longitude message_text nil))
    ([title latitude longitude message_text parse_mode]
        {
            :type "location"
            :id (helpers/uuid)
            :title title
            :latitude latitude
            :longitude longitude
            :input_message_content {
                :message_text message_text
                :parse_mode parse_mode       
            }
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