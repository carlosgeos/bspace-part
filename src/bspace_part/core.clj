(ns bspace-part.core
  (:gen-class)
  (:require [bspace-part.common :refer :all]
            [bspace-part.geometry :refer :all]
            [clojure.pprint :as pp]
            [quil.core :as q]
            [quil.middleware :as m]))

(def segments (disjoint_segments 200))

(defn setup []
  (q/frame-rate 60)
  (q/background 250)
  (q/fill 0)

  (doseq [seg segments]
    (let [[x1 y1 x2 y2] (list_of_points seg)]
      ;; (q/ellipse x1 y1 5 5)
      ;; (q/ellipse x2 y2 5 5)
      ;; (q/text (str "(" x1 ", " y1 ")") (+ 5 x1) (+ 5 y1))
      ;; (q/text (str "(" x2 ", " y2 ")") (+ 5 x2) (+ 5 y2))
      (q/line x1 y1 x2 y2))))


(defn randauto
  [segments]
  (if (empty? segments)
    segments
    (let [chosen (first segments)
          seg_list (rest segments)
          left (filter (partial segment_on_one_side chosen) seg_list)
          right  (filter (partial segment_on_other_side chosen) seg_list)
          intersects (filter (partial line_seg_intersect? chosen) seg_list)]
      {:seg chosen
       :left (->> (map (partial half_segments chosen neg?) intersects)
                  (concat left ,,,)
                  (remove nil? ,,,)
                  (randauto ,,,))
       :right (->> (map (partial half_segments chosen pos?) intersects)
                   (concat right ,,,)
                   (remove nil? ,,,)
                   (randauto ,,,))})))

(q/defsketch bsp-sketch
  :title "Binary Space Partitions"
  :settings #(q/smooth 2)
  :setup setup
  :middleware [m/fun-mode]
  :size [WIDTH HEIGHT])


(defn -main []
  (prn "BSpace partitions")
  (pp/pprint (randauto segments)))
