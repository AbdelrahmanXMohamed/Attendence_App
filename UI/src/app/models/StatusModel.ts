import { UserModel } from './UserModel'
import { DailyStatus } from './DailyStatus'

export interface StatusModel {
  day: Date;

  user: UserModel;
  
  status: DailyStatus;
}
