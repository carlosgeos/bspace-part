(ns bspace-part.core
  (:gen-class)
  (:require [bspace-part.common :refer :all]
            [bspace-part.geometry :refer :all]
            [clojure.pprint :as pp]
            [quil.core :as q]
            [quil.middleware :as m]))

(def segments (disjoint_segments 50))

(defn setup []
  (q/frame-rate 60)
  (q/background 255)

  (doseq [seg segments]
    (let [[x1 y1 x2 y2] (list_of_points seg)]
      (q/line x1 y1 x2 y2))))

(defn randauto [segments]
  (if (empty? segments)
    segments
    (let [chosen (first segments)
          seg_list (rest segments)
          left_filter (partial segment_on_one_side chosen)
          right_filter (partial segment_on_other_side chosen)
          intersects_filter (partial line_seg_intersect? chosen)]
      {:seg chosen
       :left (randauto (filter left_filter seg_list))
       :right (randauto (filter right_filter seg_list))})))

;; (defn draw []
;;   (q/stroke 0)
;;   (q/stroke-weight 2)
;;   (q/fill (q/random 255))

;;   (let [[x1 y1 x2 y2] (random_segment)] ;unpack values
;;     (q/line x1 y1 x2 y2)))

(q/defsketch bsp-sketch
  :title "Binary Space Partitions"
  :settings #(q/smooth 2)
  :setup setup
  :middleware [m/fun-mode]
  :size [WIDTH HEIGHT])


(defn -main []
  (prn "BSpace partitions")
  (pp/pprint (randauto segments)))
