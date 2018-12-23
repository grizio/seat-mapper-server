package sms.router

import java.util.UUID

import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.server.PathMatcher.{Matched, Unmatched}
import akka.http.scaladsl.server.{PathMatcher, PathMatcher1}
import sms.model.SeatMap

object PathMatchers {

  object SeatMapId extends ValueClassMatcher(segment => SeatMap.Id(UUID.fromString(segment)))

  abstract class ValueClassMatcher[A](op: String => A) extends PathMatcher1[A] {
    def apply(path: Path): PathMatcher.Matching[Tuple1[A]] = path match {
      case Path.Segment(segment, tail) =>
        try {
          Matched(tail, Tuple1(op(segment)))
        } catch {
          case _: IllegalArgumentException => Unmatched
        }
      case _ => Unmatched
    }
  }

}
