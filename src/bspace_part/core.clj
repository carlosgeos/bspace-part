(ns bspace-part.core
  (:require [quil.core :as q])
  (:gen-class))

(defn setup []
  (q/frame-rate 60)                    ;; Set framerate to 1 FPS
  (q/background 255))                 ;; Set the background colour to
;; a nice shade of grey.
(defn draw []
  (q/stroke 0)             ;; Set the stroke colour to a random grey
  (q/stroke-weight 2)       ;; Set the stroke thickness randomly
  (q/fill (q/random 255))               ;; Set the fill colour to a random grey

  (let [x1    (q/random (q/width))
        y1    (q/random (q/height))
        x2    (q/random (q/width))
        y2    (q/random (q/height))]
    (q/line x1 y1 x2 y2)))         ;; Draw a circle at x y with the correct diameter

(q/defsketch example                  ;; Define a new sketch named example
  :title "Binary Space Partitions"    ;; Set the title of the sketch
  :settings #(q/smooth 2)             ;; Turn on anti-aliasing
  :setup setup                        ;; Specify the setup fn
  :draw draw                          ;; Specify the draw fn
  :size [1080 1080])                    ;; You struggle to beat the golden ratio


(defn -main []
  (prn "Hello"))
