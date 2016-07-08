(ns telegram-bot-lib.core
  (:require [clojure.core.async :as async]
            [clj-http.client :as client]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.data.zip.xml :as navigation]
            [tblibrary.bot :as bot]
            [tblibrary.helpers :as helpers]
            [tblibrary.updater :as updater]
            [tblibrary.handlers :as handlers]
            [tblibrary.filters :as filters]
            [tblibrary.inline :as inline]
            [tblibrary.chat-action :as chat-action]
            [clojure.string :as string]
            [tblibrary.emoji :as emoji])
  (:gen-class))

(def bot-token "***REMOVED***")

(def myanimelist-search-api "http://myanimelist.net/api/anime/search.xml")
(def myanimelist-auth ["***REMOVED***" "***REMOVED***"])

(defn read-users
  ([]
    (read-users "./users.txt"))
  ([file]
    (if (.exists (as-file file))
        (clojure.java.io/with-open [rdr (clojure.java.io/reader file)]
          (for [line (line-seq rdr)]
            (string/split line #" ")))
        [])))

(defn write-users
  ([users]
    (write-users users "./users.txt"))
  ([users file]
    (clojure.java.io/with-open [wrtr (clojure.java.io/writer file)]
          (doseq [user users]
            (.write wrtr (string/join " " user))))
    users))

(defn zip-str [s]
  (zip/xml-zip 
      (xml/parse (java.io.ByteArrayInputStream. (.getBytes s)))))

(defn echo [data]
  (let [id (get-in data [:message :chat :id])
        text (get-in data [:message :text])]
        (println "ECHO_ANSWER: ")
        (println id)
        (println text)
        (bot/send_message bot-token id text nil "Markdown")))

(defn timer [data]
  (let [id (get-in data [:message :chat :id])
        text (get-in data [:message :text])
        args (handlers/parse_command_arguments text)]
        (if (nil? (re-find  #"\d+" (first args)))
            (bot/send_message bot-token id "Argument must be a number!")
            (let [t (helpers/parse_int (first args))]
              (async/go
                  (async/<! (async/timeout t))
                  (bot/send_message bot-token id (str "Beep after " t " ms!")))))))

(defn send_photo [data]
  (let [id (get-in data [:message :chat :id])]
    (bot/send_photo bot-token id (clojure.java.io/file "touhou.jpg"))))

(defn send_audio [data]
  (let [id (get-in data [:message :chat :id])]
    (bot/send_chat_action bot-token id chat-action/upload_audio)
    (bot/send_audio bot-token id (clojure.java.io/file "Radio Protector.mp3"))))

(defn send_document [data]
  (let [id (get-in data [:message :chat :id])]
    (bot/send_document bot-token id (clojure.java.io/file "LICENSE"))))

(defn get_user [data]
  (let [id (get-in data [:message :from :id])]
    (println (bot/get_user_profile_photos bot-token id))))

(defn get_anime_title [anime]
  (first (navigation/xml-> anime
        :title
        navigation/text)))

(defn get_anime_image [anime]
  (first (navigation/xml-> anime
        :image
        navigation/text)))

(defn create_inline_query [body]  
  (let [anime (navigation/xml-> body
                   :anime
                   :entry)]
        (vec (map #(inline/create_result_article (get_anime_title %) (get_anime_image %)) anime))))

(defn inline_handler [data]
  (println "INLINE: ")
  (let [id (get-in data [:inline_query :id])
        iq (get-in data [:inline_query :query])]
        (let [answer (client/get myanimelist-search-api 
                            {:query-params {:q iq}
                             :basic-auth myanimelist-auth})
              body (zip-str (:body answer))
              r (create_inline_query body)]
              (if (not (nil? body))
                  (bot/answer_inline_query bot-token id r))))) ;; (bot/answer_inline_query bot-token id results) [(inline/create_result_article "Anime" (first r))]

(def h [
  (handlers/create_command "start" #(bot/send_message bot-token (get-in % [:message :chat :id]) (str "HI! " emoji/WINKING_FACE)))
  (handlers/create_command "help" #(bot/send_message bot-token (get-in % [:message :chat :id]) "HELP!"))
  (handlers/create_command "set" timer)
  (handlers/create_command "cirno" send_photo)
  (handlers/create_command "song" send_audio)
  (handlers/create_command "license" send_document)
  (handlers/create_status_handler :new_chat_title #(bot/send_message bot-token (get-in % [:message :chat :id]) "WOW!"))
  (handlers/create_status_handler :left_chat_member #(bot/send_message bot-token (get-in % [:message :chat :id]) "WAIT!"))
  (handlers/create_inline_query_handler inline_handler)
  (handlers/create_handler filters/text get_user)
  ])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (bot/get_me bot-token))
  (let [r (client/get myanimelist-search-api {:query-params {:q "full metal asd asd asd adas asd asd"}
                                                                     :basic-auth myanimelist-auth})]
    (println (:body r)))
  ;;(println (bot/send_message "***REMOVED***" 53941045 "kokoko"))
  (updater/start_handlers h (updater/start_polling bot-token 100 1000 0))
  ;;(updater/start_handlers h (updater/start_webhook bot-token "***REMOVED***.tk" 8443 "hook" "cert.pem" "cert.keystore" "***REMOVED***"))
  (updater/idle))
