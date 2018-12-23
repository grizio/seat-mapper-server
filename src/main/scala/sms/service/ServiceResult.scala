package sms.service

import sms.model.SeatMap

sealed trait ServiceResult

object ServiceResult {
  case class FoundMany(seatMaps: Seq[SeatMap]) extends ServiceResult
  case class FoundOne(seatMap: SeatMap) extends ServiceResult
  case object NotFound extends ServiceResult
  case object InvalidUpdateKey extends ServiceResult
  case class Created(seatMap: SeatMap) extends ServiceResult
  case class Updated(seatMap: SeatMap) extends ServiceResult
  case object Deleted extends ServiceResult
}
