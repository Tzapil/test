(ns telegram-bot-lib.updater
    (:require [clojure.core.async :as async]
              [telegram-bot-lib.bot :as bot]
              [telegram-bot-lib.helpers :as helpers]))

(defn _start_polling [token c]
    (async/go-loop [offset 0]
            (async/<! (async/timeout 1000))   ;; 1 sec pause
            (recur (try
                        (let [updates (bot/get_updates token offset)
                              json (helpers/body_json updates)
                              updates (count json)]
                            (if (= updates 0)
                                offset
                                (do
                                    (async/>! c json)
                                    (inc (reduce #(max %1 (get %2 :update_id)) offset json)))))
                        (catch Exception e
                            (println (str "Caught exception: " (.getMessage e)))
                            offset))))
    c)

(defn _handle [json [handler & other]]
    (println "TEST")
    (if ((:pr handler) json)
            ((:f handler) json)
            (if (> (count other) 0)
                (_handle json other))))

(defn start_handlers [handlers c]
    (async/go-loop []
        (let [data (async/<! c)]
            (println "Got a value in this loop:" data)
            (doseq [json data]
                (_handle json handlers))
            (recur)))
    c)

(defn start_polling [token]
    (let [c (async/chan)]
        (_start_polling token c)))

(defn idle 
    ([] 
        (idle 1000))
    ([timeout] 
        (loop []
            (async/<!! (async/timeout timeout))
            (recur))))