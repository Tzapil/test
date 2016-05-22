(ns telegram-bot-lib.helpers
    (:require [clojure.data.json :as json]))

(defn body_json [req]
    (let [result (json/read-str (:body req) :key-fn keyword)]
          (:result result)))

(defn json_stringify [data]
    (json/write-str data))

(defn uuid [] (.toString (java.util.UUID/randomUUID)))

(defn filter_simple_hash [hm]
    (into {} (filter #(not (nil? (second %))) hm)))

(defn filter_hash [hm]
    (filter_simple_hash (into {} (map (fn [[name value]]
        (if (instance? clojure.lang.PersistentArrayMap value) 
            {name (filter_hash value)}
            (if (instance? clojure.lang.PersistentVector value) 
                {name (map #(filter_hash %) value)}
                {name value}))) hm))))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s)))