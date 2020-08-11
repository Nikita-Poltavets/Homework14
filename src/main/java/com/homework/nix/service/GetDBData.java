package com.homework.nix.service;

public interface GetDBData {

    int getN();

    String getNAMEById(int id);

    int getP(int locationId);

    int getNR(int locationId, int neighbourIndex);

    int getCost(int locationId, int neighborIndex);

    int getR();

    int getIdNAME1(int index);

    int getIdNAME2(int index);

    int getProblemIdByFromIdAndToId(int from_id, int to_id);
}
