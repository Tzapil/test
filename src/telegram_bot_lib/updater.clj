(ns telegram-bot-lib.updater
    (:require [clojure.core.async :as async]
              [telegram-bot-lib.bot :as bot]
              [telegram-bot-lib.helpers :as helpers]))

(defn _start_polling [token c offset]
    (async/go-loop []
            (try
                (let [updates (bot/get_updates token @offset)
                      json (helpers/body_json updates)
                      updates (count json)]
                    (if (> updates 0)
                        (async/>!! c json)))
                (catch Exception e
                    (str "Caught exception: " (.getMessage e))))
            (async/<! (async/timeout 1000))   ;; 1 sec pause
            (recur)))

(defn start_polling [token handler]
    (let [c (async/chan)
          offset (atom nil)]
        (_start_polling token c offset)
        (async/go-loop []
            (let [data (async/<! c)]
                (println "Got a value in this loop:" data)
                (handler data)
                (reset! offset (inc (reduce #(max %1 (get %2 :update_id)) 0 data)))
                (recur)))))