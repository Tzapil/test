(ns telegram-bot-lib.updater
    (:require [clojure.core.async :as async]
              [telegram-bot-lib.bot :as bot]
              [telegram-bot-lib.helpers :as helpers]))

(defn _start_polling [token]
    (let [c (async/chan)]
        (async/go-loop []
            (try
                (let [updates (bot/get_updates token)
                      json (helpers/body_json updates)]
                    (println "UPDATES")
                    (println json)
                    (async/>!! c json))
                (catch Exception e
                    (str "Caught exception: " (.getMessage e))))
            (recur))
        (async/go-loop []
            (let [data (async/<! c)]
                (println "Got a value in this loop:" data))
                (recur))))