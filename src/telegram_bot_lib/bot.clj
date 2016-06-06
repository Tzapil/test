(ns telegram-bot-lib.bot
    (:require [clj-http.client :as client]
              [telegram-bot-lib.helpers :as helpers]))

(def base_url "https://api.telegram.org/bot")

(def base-json {
        :content-type :json
        :accept :json
    })

(defn message [url data]
    (let [fdata (helpers/filter_hash data)]
        (client/post url (assoc base-json :form-params fdata))))

(defn get_me [token] 
    (helpers/body_json (client/get (str base_url token "/getMe"))))

(defn send_message 
    ([token chat_id text]
        (send_message token chat_id text nil nil))
    ([token chat_id text reply_to_message_id]
        (send_message token chat_id text reply_to_message_id nil))
    ([token chat_id text reply_to_message_id parse_mode] 
        (let [url (str base_url token "/sendMessage")
              data {:chat_id chat_id
                    :text text
                    :reply_to_message_id reply_to_message_id
                    :parse_mode parse_mode}]
                    (message url data))))

(defn forward_message [token chat_id from_chat_id message_id] 
    (let [url (str base_url token "/forwardMessage")
          data {:chat_id chat_id
                :from_chat_id from_chat_id
                :message_id message_id}]
                (message url data)))

(defn send_sticker [token chat_id sticker] 
    (let [url (str base_url token "/sendSticker")
          data {:chat_id chat_id
                :sticker sticker}]
                (message url data)))

(defn send_location [token chat_id latitude longitude] 
    (let [url (str base_url token "/sendSticker")
          data {:chat_id chat_id
                :latitude latitude
                :longitude longitude}]
                (message url data)))

(defn send_chat_action [token chat_id action] 
    (let [url (str base_url token "/sendChatAction")
          data {:chat_id chat_id
                :action action}]
                (message url data)))

(defn get_updates 
    ([token]
        (get_updates token nil 100 0))
    ([token offset]
        (get_updates token offset 100 0))
    ([token offset limit]
        (get_updates token offset limit 0))
    ([token offset limit timeout]
        (let [url (str base_url token "/getUpdates")]
                    (message url {
                            :offset offset
                        }))))

(defn answer_inline_query [token inline_query_id results] 
    (let [url (str base_url token "/answerInlineQuery")
          data {:inline_query_id inline_query_id
                :results results}]
                (message url data)))

(defn set_webhook
    ([token webhook_url]
        (let [url (str base_url token "/setWebhook")
              data {:url webhook_url}]
                    (message url data)))
    ([token webhook_url certificate]
        (let [url (str base_url token "/setWebhook")
              data {:multipart [{:name "url" :content webhook_url}
                                {:name "certificate" :content (clojure.java.io/file certificate)}]}]
                    (client/post url data))))

(defn remove_webhook [token] 
    (set_webhook token ""))

;; send_photo method
(defmulti send_photo 
    (fn [token chat_id photo & others]
        (type photo)))

;; photo = id of existed file
(defmethod send_photo java.lang.String
    ([token chat_id photo]
        (send_photo token chat_id photo nil))
    ([token chat_id photo caption]
        (let [url (str base_url token "/sendPhoto")
              data {:chat_id chat_id
                    :photo photo
                    :caption caption}]
                    (message url data))))

;; photo = java.io.File
(defmethod send_photo java.io.File
    ([token chat_id photo]
        (send_photo token chat_id photo nil))
    ([token chat_id photo caption]
        (let [url (str base_url token "/sendPhoto")
              data {:multipart (helpers/filter_multipart 
                                [{:name "chat_id" :content (str chat_id)}
                                 {:name "caption" :content caption}
                                 {:name "photo" :content photo :filename (.getName photo)}])}]
                    (client/post url data))))

;; send_audio method
(defmulti send_audio 
    (fn [token chat_id audio & others]
        (type audio)))

;; audio = id of existed file
(defmethod send_audio java.lang.String
    ([token chat_id audio]
        (send_audio token chat_id audio nil nil))
    ([token chat_id audio title]
        (send_audio token chat_id audio title nil))
    ([token chat_id audio title performer]
        (let [url (str base_url token "/sendAudio")
              data {:chat_id chat_id
                    :audio audio
                    :title title
                    :performer performer}]
                    (message url data))))

;; audio = java.io.File
(defmethod send_audio java.io.File
    ([token chat_id audio]
        (send_audio token chat_id audio nil nil))
    ([token chat_id audio title]
        (send_audio token chat_id audio title nil))
    ([token chat_id audio title performer]
        (let [url (str base_url token "/sendAudio")
              data {:multipart (helpers/filter_multipart 
                                [{:name "chat_id" :content (str chat_id)}
                                 {:name "title" :content title}
                                 {:name "performer" :content performer}
                                 {:name "audio" :content audio :filename (.getName audio)}])}]
                    (client/post url data))))

;; send_document method
(defmulti send_document 
    (fn [token chat_id document & others]
        (type document)))

;; document = id of existed file
(defmethod send_document java.lang.String
    ([token chat_id document]
        (send_photo token chat_id document nil))
    ([token chat_id document caption]
        (let [url (str base_url token "/sendDocument")
              data {:chat_id chat_id
                    :document document
                    :caption caption}]
                    (message url data))))

;; document = java.io.File
(defmethod send_document java.io.File
    ([token chat_id document]
        (send_photo token chat_id document nil))
    ([token chat_id document caption]
        (let [url (str base_url token "/sendDocument")
              data {:multipart (helpers/filter_multipart 
                                [{:name "chat_id" :content (str chat_id)}
                                 {:name "caption" :content caption}
                                 {:name "document" :content document :filename (.getName document)}])}]
                    (client/post url data))))