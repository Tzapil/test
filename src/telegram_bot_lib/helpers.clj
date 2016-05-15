(ns telegram-bot-lib.helpers
    (:require [clojure.data.json :as json]))

(defn body_json [req]
    (let [result (json/read-str (:body req) :key-fn keyword)]
          (:result result)))

(defn json_stringify [data]
    (json/write-str data))