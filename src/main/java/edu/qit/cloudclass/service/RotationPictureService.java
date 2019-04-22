package edu.qit.cloudclass.service;

import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-22
 */
public interface RotationPictureService {

    ServerResponse getList(String tag);
}
