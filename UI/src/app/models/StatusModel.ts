import { User } from './User'
import { DailyStatus } from './DailyStatus'

export interface StatusModel {
  day: Date;
  user: User;
  status: DailyStatus;
}
