(ns telegram-bot-lib.core
  (:require [telegram-bot-lib.bot :as bot]
            [telegram-bot-lib.helpers :as helpers]
            [telegram-bot-lib.updater :as updater]
            [telegram-bot-lib.handlers :as hndlrs])
  (:gen-class))

(def bot-token "141043767:AAGOD1ZEAvzNUuii6_Zxy-zydbU2x5z77so")

(defn echo [data]
    (println "ECHO_ANSWER: ")
    (println data)
    (println (get-in data [:message :chat :id]))
    (println (get-in data [:message :text]))
    (println (get-in data [:message :message_id]))
    (bot/send_message bot-token (get-in data [:message :chat :id]) (get-in data [:message :text])))

(def handlers [
    (hndlrs/add_command "help" #((println "SEND")(bot/send_message bot-token (get-in % [:message :chat :id]) "HELP HELP HUELP")))
    {
        :pr (fn [m] true)
        :f echo
    }])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (bot/get_me bot-token))
  ;;(println (bot/send_message "141043767:AAGOD1ZEAvzNUuii6_Zxy-zydbU2x5z77so" 53941045 "kokoko"))
  (updater/start_handlers handlers (updater/start_polling bot-token))
  ;;(.addShutdownHook (Runtime/getRuntime) (Thread. (fn [] (println "Shutting down..."))))
  (updater/idle))
