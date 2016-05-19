(ns telegram-bot-lib.inline)

(defn uuid [] (.toString (java.util.UUID/randomUUID)))

(defn create_result_article
    ([title message_text]
        (create_result_article title message_text nil nil))
    ([title message_text description]
        (create_result_article title message_text description nil))
    ([title message_text description parse_mode]
        {
            :type "article"
            :id (uuid)
            :description description
            :input_message_content {
                :message_text message_text
                :parse_mode parse_mode       
            }
        }))