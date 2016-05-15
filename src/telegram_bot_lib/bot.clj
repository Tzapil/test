(ns telegram-bot-lib.bot
    (:require [clj-http.client :as client]
              [telegram-bot-lib.helpers :as helpers]))

(def base_url "https://api.telegram.org/bot")

(def base-json {
        :content-type :json
        :socket-timeout 1000  ;; in milliseconds
        :conn-timeout 1000    ;; in milliseconds
        :accept :json
    })

(defn message [url data] 
    (client/post url (assoc base-json :form-params data)))

(defn get_me [token] 
    (helpers/body_json (client/get (str base_url token "/getMe"))))

(defn send_message [token chat_id text] 
    (let [url (str base_url token "/sendMessage")
          data {:chat_id chat_id
                :text text}]
                (message url data)))

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

;;(defn answer_inline_query [token inline_query_id results] 
;;    (let [url (str base_url token "/answerInlineQuery")
;;          data {:inline_query_id inline_query_id
;;                :results results}]
;;                (message token url data)))

(defn get_updates [token] 
    (let [url (str base_url token "/getUpdates")]
                (message url {})))

(defn set_webhook [token webhook_url] 
    (let [url (str base_url token "/setWebhook")
          data {:url webhook_url}]
                (message url data)))

(defn remove_webhook [token] 
    (set_webhook token ""))
