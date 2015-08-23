(ns reddit-clj.routes.home
  (:require [reddit-clj.layout :as layout]
            [reddit-clj.reddit :as reddit]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [taoensso.timbre :as timbre]))

(defn home-page []
  (layout/render "home.html" {:items (reddit/get-page-items)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))

