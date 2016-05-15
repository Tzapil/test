(ns telegram-bot-lib.core
  (:require [telegram-bot-lib.bot :as bot]
            [telegram-bot-lib.helpers :as helpers]
            [telegram-bot-lib.updater :as updater])
  (:gen-class))

(def bot-token "***REMOVED***")

(defn echo [data]
    (println "ECHO_ANSWER: ")
    (println data)
    (println (get-in data [:message :chat :id]))
    (println (get-in data [:message :text]))
    (println (get-in data [:message :message_id]))
    (bot/send_message bot-token (get-in data [:message :chat :id]) (get-in data [:message :text])))

(defn echo_handler [[data & other]]
    (if (= 0 (count other))
        (echo data)
        (echo_handler other)))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (bot/get_me bot-token))
  ;;(println (bot/send_message "***REMOVED***" 53941045 "kokoko"))
  (updater/start_polling bot-token echo_handler)
  (loop [] (recur)))
