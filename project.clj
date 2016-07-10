(defproject telegram-bot-lib "0.1.0-SNAPSHOT"
  :description "Simple library to create bots with telegram API"
  :url "http://example.com/FIXME"
  :license {:name "GPLv3"
            :url "http://www.gnu.org/licenses/gpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "2.2.0"]
                 [cheshire "5.5.0"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/data.zip "0.1.2"]
                 [tblibrary "0.1.0-SNAPSHOT"]]
  :main ^:skip-aot telegram-bot-lib.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
