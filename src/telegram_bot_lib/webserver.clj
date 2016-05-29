(ns telegram-bot-lib.webserver
    (:require [ring.adapter.jetty :as jetty]))

(defn start_server
    ([port keystore key handler]
        (jetty/run-jetty handler {
            :port 8080
            :ssl? true
            :ssl-port port
            :join? false
            :keystore keystore
            :key-password key
        })))