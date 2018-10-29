(ns bspace-part.core
  (:gen-class)
  (:require [quil.core :as q])
  (:import (java.util.concurrent ThreadLocalRandom)))

(def MAX_SEGMENT_LENGTH 50)
(def WIDTH 1080)
(def HEIGHT 1080)

(def rand_number_gen (ThreadLocalRandom/current))

(defn segments_intersect?
  "Returns a boolean that says if the two segments intersect or not"
  [s1 s2]
  )


(defn random_epsilon
  "Random integer in the range [-MAX_SEGMENT_LENGTH, MAX_SEGMENT_LENGTH]"
  []
  (.nextInt rand_number_gen (- MAX_SEGMENT_LENGTH) MAX_SEGMENT_LENGTH))

(defn random_segment
  "Return a random segment of max size MAX_SEGMENT_LENGTH. The return
  format is [x1 y1 x2 y2]"
  []
  (let [x1 (rand-int WIDTH)
        y1 (rand-int HEIGHT)]
    [x1 y1 (+ x1 (random_epsilon)) (+ y1 (random_epsilon))]))

(defn setup []
  (q/frame-rate 60)   ;; Set framerate to 1 FPS
  (q/background 255))                 ;; Set the background colour to
;; a nice shade of grey.
(defn draw []
  (q/stroke 0)             ;; Set the stroke colour to a random grey
  (q/stroke-weight 2)       ;; Set the stroke thickness randomly
  (q/fill (q/random 255))               ;; Set the fill colour to a random grey

  (let [[x1 y1 x2 y2] (random_segment)] ;unpack values
    (q/line x1 y1 x2 y2)))

(q/defsketch example                  ;; Define a new sketch named example
  :title "Binary Space Partitions"    ;; Set the title of the sketch
  :settings #(q/smooth 2)             ;; Turn on anti-aliasing
  :setup setup                        ;; Specify the setup fn
  :draw draw                          ;; Specify the draw fn
  :size [WIDTH HEIGHT])


(defn -main []
  (prn "BSpace partitions"))
