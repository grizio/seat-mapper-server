package sms.persistence

import scalikejdbc.{DB, DBSession}

import scala.concurrent.{ExecutionContext, Future}

object Database {
  def query[A](op: DBSession => A)(implicit executionContext: ExecutionContext): Future[A] = {
    Future(DB.readOnly(op))
  }

  def command[A](op: DBSession => A)(implicit executionContext: ExecutionContext): Future[A] = {
    Future(DB.localTx(op))
  }
}
